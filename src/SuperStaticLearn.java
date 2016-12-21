

public class SuperStaticLearn {
	 public static void main(String[] args){
	 System.out.println("我是main方法，我输出Super的类变量i："+Sub.i);
	 Sub sub = new Sub();
	 }
	
	}
	class Super{
	 {
	 System.out.println("我是Super成员块");
	 }
	 public Super(){
	 System.out.println("我是Super构造方法");
	 }
	 {
	 int j = 123;
	 System.out.println("我是Super成员块中的变量j："+j);
	 }
	 static{
	 System.out.println("我是Super静态块");
	 i = 123;
	 }
	 protected static int i = 1;
	}
	class Sub extends Super{
	 static{
	 System.out.println("我是Sub静态块");
	 }
	 public Sub(){
		 
		

		 
	 System.out.println("我是Sub构造方法");
	 }
	 {
	 System.out.println("我是Sub成员块");
	 }
	}