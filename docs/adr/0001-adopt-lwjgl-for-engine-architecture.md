# ADR-0001: Adoption of LWJGL for engine architecture

### Status
Accepted

## Context
This project requires a flexible, performant, and future-proof Java game development framework. The goal is to develop a game with potential for both 2D and 3D implementations. Important factors include:

- Cross-platform compatibility
- Active community and support ecosystem
- Strong documentation and learning resources
- Compatibility with modern graphical and audio APIs
- Ability to modularize the architecture and selectively integrate supporting tools
- Preference for Maven over Gradle in project configuration
- Prior hands-on challenges with other frameworks

A spike was conducted comparing three Java-based options: LWJGL, LibGDX, and jMonkeyEngine.

## Decision
We will use **LWJGL (Lightweight Java Game Library)** as the foundation for this game's engine and rendering stack.

### Consequences

#### Positive
- Offers low-level access to OpenGL, Vulkan, OpenCL, and OpenAL for graphics, computing, and audio needs
- Promotes custom engine design with minimal overhead and maximum control
- Supported by a large, active community (forums, Discord, open-source examples)
- Allows selective integration of third-party tools or custom solutions as needed
- Cross-platform support enables wide distribution potential
- Strong documentation and a history of use in real-world projects (e.g., Minecraft)

#### Negative
- Requires more up-front development for engine features like GUI, physics, asset pipelines
- Higher learning curve due to low-level abstraction
- Lacks built-in editor or project scaffolding—setup and tooling are DIY
- May take longer to prototype due to absence of prebuilt components

#### Implementation Details
- Project will use **Maven** for dependency and project management
- External utilities (e.g., NanoVG, Assimp, stb, Bullet) may be added as needed
- An internal utilities layer will be developed to handle reusable abstractions (e.g., windowing, input, rendering setup)
- Spike research and comparison findings will remain archived for future reference
- Build targets will be cross-platform by default (Windows, macOS, Linux)

### References
- [LWJGL Official Site](https://www.lwjgl.org/)
- [LWJGL GitHub Repository](https://github.com/LWJGL/lwjgl3)
- [Spike Findings Document](https://github.com/BrendonButler/entropy/wiki/GH%E2%80%904-%5BSpike%5D-Investigate-and-document-Java-game-library-options)
