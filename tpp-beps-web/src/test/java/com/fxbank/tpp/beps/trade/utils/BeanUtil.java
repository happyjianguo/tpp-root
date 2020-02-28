package com.fxbank.tpp.beps.trade.utils;

import com.fxbank.cip.base.anno.EsbSimuAnno;
import com.fxbank.tpp.beps.pmts.BEPS_351_001_01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author : 周勇沩
 * @description: Bean反射填充
 * @Date : 2020/2/28 10:37
 */
public class BeanUtil {

    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * @Description: 获取当前类及父类的所有属性
     * @Author: 周勇沩
     * @Date: 2019-12-27 08:17:33
     */
    private static List<Field> classField(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        for (Field field : clazz.getSuperclass().getDeclaredFields()) {
            if (field.getAnnotation(EsbSimuAnno.EsbField.class) != null) {
                fields.add(field);
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(EsbSimuAnno.EsbField.class) != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static void fillBean(Object bean) throws IntrospectionException {
        Class clazz = bean.getClass();
        for (Field field : classField(clazz)) {
            EsbSimuAnno.EsbField esbField = field.getAnnotation(EsbSimuAnno.EsbField.class);
            if (esbField == null) {
                continue;
            }
            Class<?> fieldType = field.getType();
            String type = esbField.type();
            if (type == null || type.length() == 0) {
                logger.error("域属性未定义type属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义type属性");
            }

            int len = esbField.len();

            int scale = esbField.scale();
            if (scale == 0 && type.equals("Double")) {
                logger.error("域属性未定义scale属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义scale属性");
            }
            String defaultValue = esbField.value();
            if (defaultValue == null && (type.equals("Date") || type.equals("Enum"))) {
                logger.error("域属性未定义defaultValue属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义defaultValue属性");
            }

            Method methodWrite = null;
            Method methodRead = null;
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                methodWrite = pd.getWriteMethod();
                methodRead = pd.getReadMethod();
            } catch (Exception e) {
                logger.error("域set属性未定义[" + field.getName() + "]", e);
                throw new RuntimeException("域set属性未定义", e);
            }
            try {
                String value = null;
                if (type.equals("Hanzi")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomHanzi(len);
                    }
                    methodWrite.invoke(bean, value);
                } else if (type.equals("String")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomString(len);
                    }
                    methodWrite.invoke(bean, value);
                } else if (type.equals("Integer")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomInteger(len);
                    }
                    methodWrite.invoke(bean, value);
                } else if (type.equals("Date")) {
                    SimpleDateFormat sdf = new SimpleDateFormat(defaultValue);
                    value = sdf.format(new Date());
                    methodWrite.invoke(bean, value);
                } else if (type.equals("Enum")) {
                    String[] valueArray = defaultValue.split(",");
                    Random random = new Random();
                    value = valueArray[random.nextInt(valueArray.length)];
                    methodWrite.invoke(bean, value);
                } else if (type.equals("Double")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomDouble(len, scale);
                    }
                    methodWrite.invoke(bean, value);
                } else if (type.equals("Object")) {
                    Object obj = methodRead.invoke(bean);
                    fillBean(obj);
                } else if (type.equals("List")) {
                    Type ptype = field.getGenericType();
                    ParameterizedType p = (ParameterizedType) ptype;
                    Type[] pArr = p.getActualTypeArguments();
                    if (pArr.length != 1) {
                        logger.error("循环内容节点范型定义错误[" + field.getName() + "]");
                        throw new RuntimeException("循环内容节点范型定义错误");
                    }
                    Class<?> clazzType = (Class<?>) pArr[0];
                    List<Object> innerList = new ArrayList<Object>();
                    for (int j = 0; j < len; ++j) {
                        Object obj = null;
                        if(clazzType==String.class){
                            obj = getRandomString(len);
                        }else{
                            obj = toBean(clazzType);
                        }
                        innerList.add(obj);
                    }
                    methodWrite.invoke(bean, innerList);
                } else {
                    logger.error("注解type定义错误[" + type + "]");
                    throw new RuntimeException("注解type定义错误");
                }
            } catch (Exception e) {
                logger.error("域值转换异常[" + field.getName() + "][" + fieldType + "][" + type + "]", e);
                throw new RuntimeException("域值转换异常", e);
            }
        }
    }

    public static <T> T toBean(Class<?> clazz) {
        return toBean(null, clazz);
    }

    public static <T> T toBean(T bean, Class<?> clazz) {
        try {
            bean = (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            try {
                bean = (T) clazz.getDeclaredConstructors()[0].newInstance(bean);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                logger.error("创建实例异常[" + clazz + "]", e);
                logger.error("创建实例异常[" + clazz + "]", ex);
                throw new RuntimeException("创建实例异常");
            }
        }
        for (Field field : classField(clazz)) {
            EsbSimuAnno.EsbField esbField = field.getAnnotation(EsbSimuAnno.EsbField.class);
            if (esbField == null) {
                continue;
            }
            Class<?> fieldType = field.getType();
            String type = esbField.type();
            if (type == null || type.length() == 0) {
                logger.error("域属性未定义type属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义type属性");
            }

            int len = esbField.len();

            int scale = esbField.scale();
            if (scale == 0 && type.equals("Double")) {
                logger.error("域属性未定义scale属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义scale属性");
            }
            String defaultValue = esbField.value();
            if (defaultValue == null && (type.equals("Date") || type.equals("Enum"))) {
                logger.error("域属性未定义defaultValue属性[" + field.getName() + "]");
                throw new RuntimeException("域属性未定义defaultValue属性");
            }

            Method method = null;
            try {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                method = pd.getWriteMethod();
            } catch (Exception e) {
                logger.error("域set属性未定义[" + field.getName() + "]", e);
                throw new RuntimeException("域set属性未定义", e);
            }
            try {
                String value = null;
                if (type.equals("Hanzi")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomHanzi(len);
                    }
                    method.invoke(bean, value);
                } else if (type.equals("String")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomString(len);
                    }
                    method.invoke(bean, value);
                } else if (type.equals("Integer")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomInteger(len);
                    }
                    method.invoke(bean, value);
                } else if (type.equals("Date")) {
                    SimpleDateFormat sdf = new SimpleDateFormat(defaultValue);
                    value = sdf.format(new Date());
                    method.invoke(bean, value);
                } else if (type.equals("Enum")) {
                    String[] valueArray = defaultValue.split(",");
                    Random random = new Random();
                    value = valueArray[random.nextInt(valueArray.length)];
                    method.invoke(bean, value);
                } else if (type.equals("Double")) {
                    if (defaultValue != null && defaultValue.length() > 0) {
                        value = defaultValue;
                    } else {
                        value = getRandomDouble(len, scale);
                    }
                    method.invoke(bean, value);
                } else if (type.equals("Object")) {
                    Object obj = toBean(bean, fieldType);
                    method.invoke(bean, obj);
                } else if (type.equals("List")) {
                    Type ptype = field.getGenericType();
                    ParameterizedType p = (ParameterizedType) ptype;
                    Type[] pArr = p.getActualTypeArguments();
                    if (pArr.length != 1) {
                        logger.error("循环内容节点范型定义错误[" + field.getName() + "]");
                        throw new RuntimeException("循环内容节点范型定义错误");
                    }
                    Class<?> clazzType = (Class<?>) pArr[0];
                    Random random = new Random();
                    Integer cycCount = random.nextInt(len);
                    if (cycCount == 0) {
                        cycCount++;
                    }
                    List<Object> innerList = new ArrayList<Object>();
                    for (int j = 0; j < cycCount; ++j) {
                        Object obj = toBean(bean, clazzType);
                        innerList.add(obj);
                    }
                    method.invoke(bean, innerList);
                } else {
                    logger.error("注解type定义错误[" + type + "]");
                    throw new RuntimeException("注解type定义错误");
                }
            } catch (Exception e) {
                logger.error("域值转换异常[" + field.getName() + "][" + fieldType + "][" + type + "]", e);
                throw new RuntimeException("域值转换异常", e);
            }
        }
        return bean;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomInteger(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        Integer ii = Integer.valueOf(sb.toString());
        return String.valueOf(ii);
    }

    public static String getRandomDouble(int length, int scale) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - scale; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        sb.append(".");
        for (int i = 0; i < scale; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }

        Double dd = Double.valueOf(sb.toString());
        return String.valueOf(dd);
    }

    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("随机生成汉字错误", e);
        }

        return str.charAt(0);
    }

    public static String getRandomHanzi(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IntrospectionException {
        BEPS_351_001_01 beps351 = new BEPS_351_001_01(null,0,0,0);
        BeanUtil.fillBean(beps351);
        logger.info(objectToXml(beps351));
    }

    public static String objectToXml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化输出
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// 编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xml头信息
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);// 是否省略xml头信息
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);
            StringBuffer sb = new StringBuffer();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            sb.append(writer.getBuffer());
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}