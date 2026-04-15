#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 uv;

uniform mat4 uProjection;
uniform mat4 uModel;
uniform vec2 uAtlasOffset;
uniform vec2 uAtlasSize;

out vec2 fragUV;

void main() {
    fragUV = uAtlasOffset + uv * uAtlasSize;
    gl_Position = uProjection * uModel * vec4(position, 0.0, 1.0);
}
