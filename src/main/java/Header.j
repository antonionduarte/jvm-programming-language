.class public Header
.super java/lang/Object

; standard initializer

.method public <init>()V
   aload_0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method

.method public static main([Ljava/lang/String;)V

       ; set limits used by this method

       .limit locals  1 
       .limit stack 256

       ; setup local variables:
       ; the PrintStream object held in java.lang.System.out

       getstatic java/lang/System/out Ljava/io/PrintStream;

       ; place bytecodes here
       ; START
					sipush 4
					sipush 5
					iadd
					sipush 8
					sipush 2
				  	imul
					imul
       ; END 

       ; convert to String;
       invokestatic java/lang/String/valueOf(I)Ljava/lang/String;

       ; call println 
       invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V

       return
.end method





