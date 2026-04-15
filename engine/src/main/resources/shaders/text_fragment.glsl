#version 330 core

in vec2 fragUV;

uniform sampler2D uAtlas;
uniform vec4 uColor;

out vec4 fragColor;

void main() {
    fragColor = uColor * texture(uAtlas, fragUV);
}
