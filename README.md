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

## 📝 Versioning Strategy

> [!NOTE]
> This project follows [Semantic Versioning](https://semver.org/) with explicit pre-release qualifiers.

### 🔖 Version Stages

| Stage                     | Version Pattern    | Description                                          |
|---------------------------|--------------------|------------------------------------------------------|
| Pre-Alpha                 | `0.1.0-PREALPHA.N` | Early prototypes; increment `N` for each CI snapshot |
| Alpha                     | `0.2.0-ALPHA.N`    | Playable features; minor bumps for feature waves     |
| Beta                      | `0.2.0-BETA.N`     | Feature complete; patch bumps for fixes              |
| Release Candidate         | `1.0.0-RC.N`       | Final validation before GA                           |
| General Availability (GA) | `1.0.0`            | Production-ready release                             |
| Post-GA Minor             | `1.1.0`, `1.2.0`   | New features and enhancements                        |
| Post-GA Patch             | `1.0.1`, `1.0.2`   | Bug fixes and minor improvements                     |

### ⚙️ Bumping Guidelines

- Minor bumps (`0.x.0`) introduce sizable features or API changes during pre-releases.
- Patch bumps (`0.x.1`) address bug fixes or small refinements within the same stage.
- After an RC (`-RC.N`) is validated, drop the qualifier for GA (`1.0.0`).
