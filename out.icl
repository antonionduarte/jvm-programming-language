.class public Header
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
.limit locals  1
.limit stack 256
setup local variables:
the PrintStream object held in java.lang.System.out
getstatic java/lang/System/out Ljava/io/PrintStream;
; START
; END
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method
