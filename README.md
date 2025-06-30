# entropy
Ivy must resolve the disorder by battling through the mysterious force that threatens the kingdoms.

## 🏗️ Building & Packaging

This project uses Maven profiles to build native-specific versions of the game using LWJGL. Each profile bundles only the required platform-specific native libraries, resulting in leaner and more portable artifacts.

### 🔧 Platform Profiles

| Platform | Maven Profile ID | Native Classifier       | Output Artifact                           |
|----------|------------------|--------------------------|--------------------------------------------|
| Windows  | `windows`        | `natives-windows`        | `entropy-1.0.0-PREALPHA-windows.jar`       |
| macOS    | `macos`          | `natives-macos`          | `entropy-1.0.0-PREALPHA-macos.jar`         |
| Linux    | `linux`          | `natives-linux`          | `entropy-1.0.0-PREALPHA-linux.jar`         |

Each profile sets:
- `native.classifier` to pull the correct native bindings
- `platform.name` for use in artifact naming via `${finalName}`

### 📦 Build for a Specific Platform

To package for a specific OS, activate its profile:

#### Windows
```bash
mvn clean package -Pwindows
```

#### MacOS (default)
```bash
mvn clean package -Pmacos
```

#### Linux
```bash
mvn clean package -Plinux
```

