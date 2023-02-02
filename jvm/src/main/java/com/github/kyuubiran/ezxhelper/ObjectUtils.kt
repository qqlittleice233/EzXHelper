@file:Suppress("unused")

package com.github.kyuubiran.ezxhelper

object ObjectUtils {
    /**
     * Get the field object by the name in the object.
     * @param obj object
     * @param fieldName field name
     * @return field object or null
     * @throws NoSuchFieldException if the field is not found
     * @sample com.example.sample.getObjectOrNull
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    fun getObjectOrNull(obj: Any, fieldName: String): Any? = obj.javaClass.getDeclaredField(fieldName).also { it.isAccessible = true }.get(obj)

    /**
     * Get the field object by the name in the object.
     * @param obj object
     * @param fieldName field name
     * @param untilSuperClass until super class(true = break, false = continue), null = find in all superclasses.
     * @return field object or null
     * @throws NoSuchFieldException if the field is not found
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    fun getObjectOrNullUntilSuperclass(obj: Any, fieldName: String, untilSuperClass: (Class<*>.() -> Boolean)? = null): Any? {
        var clazz: Class<*> = obj.javaClass
        while (clazz != Any::class.java) {
            if (untilSuperClass?.invoke(clazz) == true) break

            try {
                return getObjectOrNull(obj, fieldName)
            } catch (e: NoSuchFieldException) {
                clazz = clazz.superclass
            }
        }
        throw NoSuchFieldException("No such field $fieldName in ${obj::class.java.name} and its superclasses.")
    }

    /**
     * Get the field object by the name in the object, and trying to cast to the [T] type.
     * @param obj object
     * @param fieldName field name
     * @return [T] field object, or null if is null or cast failed.
     * @throws NoSuchFieldException if the field is not found
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> getObjectOrNullAs(obj: Any, fieldName: String): T? = getObjectOrNull(obj, fieldName) as? T?

    /**
     * Get the field object by the name in the object, and trying to cast to the [T] type.
     * @param obj object
     * @param fieldName field name
     * @param untilSuperClass until super class(true = break, false = continue), null = find in all superclasses.
     * @return [T] field object, or null if it is null or cast failed.
     * @throws NoSuchFieldException if the field is not found
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    @Suppress("UNCHECKED_CAST")
    fun <T> getObjectOrNullUntilSuperclassAs(obj: Any, fieldName: String, untilSuperClass: (Class<*>.() -> Boolean)? = null): T? =
        getObjectOrNullUntilSuperclass(obj, fieldName, untilSuperClass) as? T?

    /**
     * Set the field object by the name in the object.
     * @param obj object
     * @param fieldName field name
     * @throws NoSuchFieldException if the field is not found
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    fun setObject(obj: Any, fieldName: String, value: Any?) =
        obj.javaClass.getDeclaredField(fieldName).also { it.isAccessible = true }.set(obj, value)

    /**
     * Set the field object by the name in the object.
     * @param obj object
     * @param fieldName field name
     * @param untilSuperClass until super class(true = break, false = continue), null = find in all superclasses.
     * @throws NoSuchFieldException if the field is not found
     */
    @JvmStatic
    @Throws(NoSuchFieldException::class)
    fun setObjectUntilSuperclass(obj: Any, fieldName: String, value: Any?, untilSuperClass: (Class<*>.() -> Boolean)? = null) {
        var clazz: Class<*> = obj.javaClass
        while (clazz != Any::class.java) {
            if (untilSuperClass?.invoke(clazz) == true) break

            try {
                return setObject(obj, fieldName, value)
            } catch (e: NoSuchFieldException) {
                clazz = clazz.superclass
            }
        }
        throw NoSuchFieldException("No such field $fieldName in ${obj::class.java.name} and its superclasses.")
    }

//    fun invokeMethod(obj: Any, methodName: String, vararg args: Any?): Any? =
//        obj.javaClass.getDeclaredMethod(methodName, *args.map { it?.javaClass ?: Any::class.java }.toTypedArray())
//            .also { it.isAccessible = true }
//            .invoke(obj, *args)
}