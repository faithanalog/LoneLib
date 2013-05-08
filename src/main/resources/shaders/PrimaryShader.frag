#version 330

uniform sampler2D texSampler;
uniform bool useTex;

in vec4 color;
in vec2 texCoords;
out vec4 outColor;

void main(void) {
	if(useTex) {
		outColor = color * texture(texSampler, texCoords);
	} else {
		outColor = color;
	}
}