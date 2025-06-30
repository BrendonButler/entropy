# entropy
Ivy must resolve the disorder by battling through the mysterious force that threatens the kingdoms.

## 🏗️ Building & Packaging

This project uses Maven profiles to build native-specific versions of the game using LWJGL. Each profile bundles only the required platform-specific native libraries, resulting in leaner and more portable artifacts.

### 🔧 Platform Profiles

| Platform | Maven Profile ID | Native Classifier | Output Artifact                 | Default |
|----------|------------------|-------------------|---------------------------------|---------|
| Windows  | `windows`        | `natives-windows` | `entropy-<version>-windows.jar` |         |
| macOS    | `macos`          | `natives-macos`   | `entropy-<version>-macos.jar`   | ✅       |
| Linux    | `linux`          | `natives-linux`   | `entropy-<version>-linux.jar`   |         |

Each profile sets:
- `native.classifier` to pull the correct native bindings
- `platform.name` for use in artifact naming via `${finalName}`

### 📦 Build for a Specific Platform

> [!TIP]
> Without specifying a profile, the default specified in the POM (see table above) will be used.

To package for a specific OS, activate its profile:

#### Windows
```bash
mvn clean package -Pwindows
```

#### macOS (default)
```bash
mvn clean package -Pmacos
```

#### Linux
```bash
mvn clean package -Plinux
```
