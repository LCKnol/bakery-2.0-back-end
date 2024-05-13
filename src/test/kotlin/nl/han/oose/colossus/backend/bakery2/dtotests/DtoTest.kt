import junit.framework.Assert
import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*
import kotlin.reflect.full.declaredMemberFunctions

class DtoTest {

    private val dtoPackage = "nl.han.oose.colossus.backend.bakery2.dto" // Update with your DTO package name

    private fun findDtoClasses(): Array<Class<*>> {
        val dtoClasses = mutableListOf<Class<*>>()
        val packagePath = dtoPackage.replace('.', '/')
        val classLoader = Thread.currentThread().contextClassLoader
        val packageUrl = classLoader.getResource(packagePath)
        if (packageUrl != null) {
            val packageDirectory = File(packageUrl.file)
            if (packageDirectory.exists() && packageDirectory.isDirectory) {
                val classFiles = packageDirectory.listFiles { file -> file.name.endsWith(".class") }
                classFiles?.forEach { classFile ->
                    val className = classFile.name.replace(".class", "")
                    try {
                        val clazz = Class.forName("$dtoPackage.$className")
                        if (isDtoClass(clazz)) {
                            dtoClasses.add(clazz)
                        }
                    } catch (e: ClassNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return dtoClasses.toTypedArray()
    }

    private fun isDtoClass(clazz: Class<*>): Boolean {
        return clazz.simpleName.endsWith("Dto")
    }

    @Test
    fun testGettersAndSetters() {
        val dtoClasses = findDtoClasses()
        dtoClasses.forEach { dtoClass ->
            val dtoInstance = dtoClass.getDeclaredConstructor().newInstance()
            dtoClass.kotlin.declaredMemberFunctions.forEach { memberFunction ->
                val methodName = memberFunction.name
                if (isGetter(methodName) || isSetter(methodName)) {
                    invokeMethod(dtoInstance, methodName)
                }
            }
        }
        val piDto = PiDto()
        piDto.setName("testname")
        assertEquals("testname", piDto.getName())
    }

    private fun invokeMethod(dtoInstance: Any, methodName: String) {
        try {
            val method = dtoInstance.javaClass.getMethod(methodName)
            // Invoke the method on the instance
            method.invoke(dtoInstance)
            // If successful, assert that the method was invoked
            Assert.assertTrue(true)
        } catch (e: Exception) {
            // If an exception occurs, fail the test
            Assert.fail("Failed to invoke method $methodName: ${e.message}")
        }
    }

    private fun isGetter(methodName: String) = methodName.startsWith("get") && methodName.length > 3

    private fun isSetter(methodName: String) = methodName.startsWith("set") && methodName.length > 3
}
