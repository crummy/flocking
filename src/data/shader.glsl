// shader example for Processing
#version 410

// uniform - variables set in Processing sketch using shader.set()
// varying - variables set by Processing itself
uniform int totalBoids;
uniform float[144] boids;
int err = 0;

out vec4 fragColor;

float distanceToNearest() {
    float nearest = 9999.9;
    for (int i = 0; i < totalBoids * 2; i = i + 2) {
        vec2 boid = vec2(boids[i], boids[i+1]);
        vec2 pixel = gl_FragCoord.xy;
        float dist = length(pixel - boid);
        if (isnan(dist) || isinf(dist)) {
            err = 1;
        }
        nearest = min(nearest, dist);
    }
    return nearest;
}

void main() {
    float d = distanceToNearest();
    vec4 colour = vec4(d / 255.0, d / 255.0, d / 255.0, 1.0);
    if (err != 0) {
        colour = vec4(1.0, 0.0, 0.0, 1.0);
    }
    fragColor = colour;
}