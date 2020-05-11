package com.wwzh.interview.study.jvm.gcroots;

/**
 * 再java中，可作为的GC Roots对象有：
 * 
 * 1. 虚拟机栈（栈帧中的局部变量区，也叫做局部变量）
 * 2. 方法区中的类静态属性引用的对象
 * 3. 方法区中常量引用的对象
 * 4. 本地方法栈中JNI（Native方法）引用的对象
 */
public class GCRootDemo {
	private byte[] byteArray = new byte[100 * 1024 * 1024];
	
	public static void main(String[] args) {
		m1();
	}
	
	public static void m1() {
		GCRootDemo gcRootDemo = new GCRootDemo();
		
		System.gc();
		
		System.out.println("第一次GC完成");
	}

}
