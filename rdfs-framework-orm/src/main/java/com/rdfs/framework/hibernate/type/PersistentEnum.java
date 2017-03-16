package com.rdfs.framework.hibernate.type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * 可持久化的枚举，此类枚举将被持久化
 * @version $Revision: 1.1 $
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PersistentEnum {

	/**
	 * 枚举代码，不写时默认使用类名作为枚举代码
	 * @return
	 */
	String code();
	
	/**
	 * 枚举名称
	 * @return
	 */
	String name() default "";
	
	/**
	 * 当值为空时取枚举的KEY，也就是枚举的引用名字（如{@link com.skyon.webframe.core.type.YesNoEnum#Y}），默认取value属性
	 * @return
	 */
	String itemCode() default "value";
	
	/**
	 * 枚举项目的值,通常是名称
	 * @return
	 */
	String itemName() default "name";
	
	/**
	 * 代码的长度
	 * @return
	 */
	int length() default 0;
	
	/**
	 * 字典描述
	 * @return
	 */
	String describe() default "";
	
	/**
	 * 当枚举未持久化时，是否自动创建它
	 * @return
	 */
	boolean autoCreate() default false;
	
	/**
	 * 是否是只读的(不可修改维护)，若想通过界面维护，需要将readonly设置成false
	 * @return
	 */
	boolean readonly() default true;
	
	/**
	 * 上级枚举代码，为空时无上级
	 * @return
	 */
	String parentCode() default "";
	
	/**
	 * 上级枚举项代码，为空时无上级
	 * @return
	 */
	String parentItemCode() default "";
}
