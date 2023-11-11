# vscode-java-ranking

This extension provides ranking on java completions based on context and defined scores for those contexts. The contexts
could be where the completions are invoked such as 

- in Test or Source
- Dependencies in the classpath etc

The scores are defined in models based on a basic type and method pattern. Currently the models are packed inside
the extension.

## Model Syntax

### Type model

The syntax used is `Qualified_Jvm_Type_Signature/Source_Or_Test_Qualifier/SimpleName=Score`. 

**Source_Or_Test_Qualifier** : Valid values are `S` for source and `T` for test code.

**Qualified_Jvm_Type_Signature** : A example signature for java List class would be `Ljava.util.List;` without generic parameters.

Example:

```
Ljava.util.List;/S/List=90
Ljava.util.Set;/S/Set=80
Ljava.util.Map;/S/Map=70
```

### Method model

The syntax used is `Qualified_Jvm_Type_Signature/Source_Or_Test_Qualifier/Method_Name/Generic_Jvm_Method_Signature=Score`. 

**Source_Or_Test_Qualifier** : Valid values are `S` for source and `T` for test code.

**Qualified_Jvm_Type_Signature** : A example signature for java List class would be `Ljava.util.List;` without generic parameters.

**Generic_Jvm_Method_Signature** : A example signature of List.of method would be `<E:Ljava.lang.Object;>(TE;)Ljava.util.List<TE;>;`

Example:

```
Ljava.util.List;/S/of/<E:Ljava.lang.Object;>(TE;)Ljava.util.List<TE;>;=90
Ljava.util.List;/S/of/<E:Ljava.lang.Object;>()Ljava.util.List<TE;>;=89
Ljava.util.List;/S/of/<E:Ljava.lang.Object;>([TE;)Ljava.util.List<TE;>;=90

# Test
Lorg.assertj.core.api.Assertions;/T/assertThat/(Ljava.lang.String;)Lorg.assertj.core.api.AbstractStringAssert<*>;=90
Lorg.assertj.core.api.Assertions;/T/assertThat/<T:Ljava.lang.Object;>(TT;)Lorg.assertj.core.api.ObjectAssert<TT;>;=90
```

## Requirements

This extension depends on `redhat.java` extension.


**Enjoy!**
