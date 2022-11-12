.class public Header
.super java/lang/Object
.method public <init>()V
aload_0
invokenonvirtual java/lang/Object/<init>()V
return
.end method
.method public static main([Ljava/lang/String;)V
.limit locals  4
.limit stack 256
getstatic java/lang/System/out Ljava/io/PrintStream;
aconst_null
astore_3
; START
sipush 4
sipush 5
iadd
sipush 8
sipush 2
imul
imul
; END
invokestatic java/lang/String/valueOf(I)Ljava/lang/String;
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method