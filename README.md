# entropy
Ivy must resolve the disorder by battling through the mysterious force that threatens the kingdoms.

## 🏗️ Building & Packaging

This project uses Maven profiles to build native-specific versions of the game using LWJGL. Each profile bundles only the required platform-specific native libraries, resulting in leaner and more portable artifacts.

### 🔧 Platform Profiles

| Platform | Maven Profile ID | Native Classifier     | Output Artifact                 | Default |
|----------|------------------|-----------------------|---------------------------------|---------|
| Windows  | `windows`        | `natives-windows`     | `entropy-<version>-windows.jar` |         |
| macOS    | `macos`          | `natives-macos-arm64` | `entropy-<version>-macos.jar`   | ✅       |
| Linux    | `linux`          | `natives-linux`       | `entropy-<version>-linux.jar`   |         |

Each profile sets:
- `native.classifier` to pull the correct native bindings
- `platform.name` for use in artifact naming via `${finalName}`

### 📦 Build for a Specific Platform

> [!TIP]
> Without specifying a profile, the default specified in the POM (see table above) will be used.

To package for a specific OS, activate its profile:

#### Linux
```bash
mvn clean package -Plinux
```

#### macOS (default)
```bash
mvn clean package -Pmacos
```

#### Windows
```bash
mvn clean package -Pwindows
```

### 🧪 Running Tests

To run tests, you need to have the native libraries for the specific platform available. You can download them from the [LWJGL website](https://www.lwjgl.org/download) (ensure the version matches your project dependencies).

> [!NOTE]
> For macOS, you will need to specify the `-XstartOnFirstThread` JVM argument to avoid threading issues.

To test the game on a specific platform, use the corresponding Maven profile. This will run the tests using the native libraries for that platform.

#### Linux
```bash
mvn test -Plinux
```

#### macOS
```bash
mvn test -Pmacos -DargLine="-XstartOnFirstThread"
```

#### Windows
```bash
mvn test -Pwindows
```

## 📝 Versioning Strategy

> [!IMPORTANT]
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

> [!NOTE]
> The is to never "_major bump_," but it may happen eventually once enough new concepts and better ways of doing things are learned and implemented.

- Major bumps (`x.0.0`) indicate significant rewrites.
- Minor bumps (`0.y.0`) introduce sizable features or API changes during pre-releases.
- Patch bumps (`0.1.z`) address bug fixes or small refinements within the same stage.
- After an RC (`-RC.N`) is validated, drop the qualifier for GA (`1.0.0`).
