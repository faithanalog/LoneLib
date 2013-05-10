#version 330
struct Light
{
	vec3 direction;
	vec4 diffuse;
	vec4 ambient;
};


uniform mat4 Projection = mat4(1.0);
uniform mat4 Model = mat4(1.0);
uniform mat4 View = mat4(1.0);
uniform mat3 NormMatrix = mat3(1.0);

//Lighting stuff
uniform bool useLighting;
uniform bool[16] enabledLights;
uniform Light[16] lights;
uniform vec4 ambient;

layout(location = 0) in vec3 vPosition;
layout(location = 1) in vec4 vColor;
layout(location = 2) in vec2 vTexCoords;

out vec4 color;
out vec2 texCoords;

vec4 getDiffuse(vec3 normal, Light light, vec4 materialColor) {
	vec3 lightDir = normalize(light.direction);
	float NdotL = max(dot(normal, lightDir), 0.0);
	return NdotL * materialColor * light.diffuse;
}

vec4 getAmbient(Light light, vec4 materialColor) {
    vec4 color = materialColor * light.ambient;
    return color;
}

vec4 getLightColor(vec3 normal, vec4 materialColor, Light light) {
    return getAmbient(light, materialColor) + getDiffuse(normal, light, materialColor);
}

void main(void) {
	gl_Position = Projection * View * Model * vec4(vPosition, 1);
	texCoords = vTexCoords;
	if(useLighting) {
		vec3 normal = normalize(NormMatrix * vec3(0, 0, -1));
		color = vColor * ambient;
		for(int i = 0; i < 16; i++) {
			if(enabledLights[i]) {
				color += getLightColor(normal, vColor, lights[i]);
			}
		}
		color = min(color, vec4(1, 1, 1, 1));
	} else {
		color = vColor;
	}
}