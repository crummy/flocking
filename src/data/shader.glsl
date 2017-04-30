// shader example for Processing
#version 410

// uniform - variables set in Processing sketch using shader.set()
// varying - variables set by Processing itself
uniform int totalBoids;
uniform float[500] boids;
int err = 0;

out vec4 fragColor;

vec2 position(int i) {
    return vec2(boids[i * 3], boids[i * 3 + 1]);
}

vec4 color(int i) {
    float flock = boids[i * 3 + 2];
    if (flock < 0.5) {
        return vec4(1.0, 1.0, 0.0, 1.0);
    } else {
        return vec4(0.0, 0.0, 1.0, 1.0);
    }
}


vec4 getColour() {
    vec4 flockColour = vec4(0.0, 0.0, 0.0, 1.0);
    for (int i = 0; i < totalBoids; i++) {
        vec2 boid = position(i);
        vec2 pixel = gl_FragCoord.xy;
        float dist = length(pixel - boid);
        if (isnan(dist) || isinf(dist)) {
            err = 1;
        }
        flockColour += color(i) * 1/dist;
    }
    return flockColour;
}

void main() {
    vec4 colour = getColour();
    if (err != 0) {
        colour = vec4(1.0, 0.0, 0.0, 1.0);
    }
    fragColor = colour;
}