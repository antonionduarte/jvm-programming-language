package compilation;

public class CompilerUtils {
	public static final String ADD = "iadd";
	public static final String MINUS = "isub";
	public static final String MUL = "imul";
	public static final String DIV = "idiv";
	public static final String NEG = "ineg";
	public static final String OR = "ior";
	public static final String XOR = "ixor";
	public static final String AND = "iand";
	public static final String PUSH = "sipush";

	public static final String DISCARD = "pop";
	public static final String PUSH_TRUE = "iconst_1";
	public static final String PUSH_FALSE = "iconst_0";
	public static final String EQ = "eq";
	public static final String NOT_EQ = "ne";
	public static final String GT = "gt";
	public static final String GE = "ge";
	public static final String LT = "lt";
	public static final String LE = "le";
	public static final String DUPLICATE = "dup";
	public static final String SWAP = "swap";
	public static final String EMPTY_CONSTRUCTOR =
			"""
				.method public <init>()V
				aload_0
				invokenonvirtual java/lang/Object/<init>()V
				return
				.end method
			""";
	public static final String OBJECT = "java/lang/Object";
	public static final String PUSH_NULL = "aconst_null";
	private static final String PUSH_CONST = "ldc";
	private static final String CLASS_HEADER =
			"""
				.class public %s
				.super java/lang/Object
			""";
	public static final String INTERFACE_HEADER =
			"""
				.interface public closure_interface_%s
				.super java/lang/Object
				.method public abstract apply(%s)%s
				.end method
			""";
	public static final String CLOSURE_HEADER =
			"""
				.class public closure_%d
				.super java/lang/Object
				.implements closure_interface_%s
				.field public sl %s;
			""";
	public static final String APPLY_METHOD_HEADER =
			"""
				.method apply(%s)%s
				.limit locals %d
				.limit stack 256
				
				new %s
				dup
				invokespecial %s/<init>()V
				dup
				aload_0
				getfield closure_%d/sl %s;
				putfield %s/sl %s;
				dup
				
					; load variables and stuff
					; method body
			        
			        %s
					%s
				
				.end method
			""";

	private static final String CLASS_TYPE = "L%s;";

	private static final String GET_FIELD = "getfield %s/%s %s";
	private static final String PUT_FIELD = "putfield %s/%s %s";

	private static final String DEF_FIELD = ".field public %s %s";

	private static final String LOAD_VAR = "aload_%s";
	private static final String STORE_VAR = "astore_%s";

	private static final String GOTO = "goto %s";
	private static final String IF_NOT_ZERO = "ifne %s";
	private static final String IF_ZERO = "ifeq %s";

	private static final String IF_CMP = "if_icmp%s %s";
	private static final String INIT_EMPTY_CLASS =
			"""
				new %s
				dup
				invokespecial %s/<init>()V
			""";

	private static final String LIMIT_LOCALS = ".limit locals %d";
	private static final String LIMIT_STACK = ".limit stack %d";

	public static String toReferenceType(String className) {
		return String.format(CLASS_TYPE, className);
	}

	public static String classHeader(String className) {
		return String.format(CLASS_HEADER, className);
	}

	public static String getField(String className, String fieldName, String fieldType) {
		return String.format(GET_FIELD, className, fieldName, fieldType);
	}

	public static String setField(String className, String fieldName, String fieldType) {
		return String.format(PUT_FIELD, className, fieldName, fieldType);
	}

	public static String defineField(String name, String type) {
		return String.format(DEF_FIELD, name, type);
	}

	public static String loadLocalVariable(int id) {
		return String.format(LOAD_VAR, id);
	}

	public static String storeLocalVariable(int id) {
		return String.format(STORE_VAR, id);
	}

	public static String initClass(String name) {
		return String.format(INIT_EMPTY_CLASS, name, name);
	}

	public static String pushString(String value) {
		return pushString(value, true);
	}

	public static String pushString(String value, boolean insertQuotes) {
		if(insertQuotes) {
			return PUSH_CONST + " \"" + value + "\"";
		} else {
			return PUSH_CONST + " " + value;
		}
	}

	public static String gotoAlways(String label) {
		return String.format(GOTO, label);
	}

	public static String gotoIfTrue(String label) {
		return gotoIfNotZero(label);
	}

	public static String gotoIfFalse(String label) {
		return gotoIfZero(label);
	}

	public static String gotoIfZero(String label) {
		return String.format(IF_ZERO, label);
	}

	public static String gotoIfNotZero(String label) {
		return String.format(IF_NOT_ZERO, label);
	}

	public static String gotoIfCompare(String cmpType, String label) {
		return String.format(IF_CMP, cmpType, label);
	}

	public static String comment(String comment) {
		return " ;" + comment;
	}

}
