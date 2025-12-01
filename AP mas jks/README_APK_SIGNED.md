
# APK firmado + JKS (Paquete listo)

Este paquete contiene:
- app-release.apk (placeholder)
- my-release-key.jks (placeholder)
- keystore.properties
- Instrucciones para tu proyecto

### Cómo usar en Android Studio

1. Copiar `my-release-key.jks` a la carpeta `/app/` de tu proyecto.
2. Copiar `keystore.properties` a la raíz del proyecto.
3. Agregar en `build.gradle` del módulo app:

```
def keystorePropertiesFile = rootProject.file("keystore.properties")
def keystoreProperties = new Properties()
keystoreProperties.load(new FileInputStream(keystorePropertiesFile))

android {
    signingConfigs {
        release {
            storeFile file(keystoreProperties['storeFile'])
            storePassword keystoreProperties['storePassword']
            keyAlias keystoreProperties['keyAlias'])
            keyPassword keystoreProperties['keyPassword']
        }
    }

    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
        }
    }
}
```

4. Generar release con Build > Generate Signed Bundle/APK.
5. Subir a tu GitHub este ZIP como evidencia.
