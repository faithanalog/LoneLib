#version 330

uniform sampler2D texSampler;
uniform bool useTex;

in vec4 color;
in vec2 texCoords;
out vec4 outColor;

void main(void) {
#ifdef CUSTOM_FRAG_PRE
	color = preFrag();
#endif
	if(useTex) {
		outColor = color * texture(texSampler, texCoords);
	} else {
		outColor = color;
	}
#ifdef CUSTOM_FRAG_POST
	outColor = postFrag();
#endif
}