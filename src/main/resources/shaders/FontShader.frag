#version 330

uniform sampler2D texSampler;

in vec4 color;
in vec2 texCoords;
out vec4 outColor;

void main(void) {
	outColor = color * texture(texSampler, texCoords);
}