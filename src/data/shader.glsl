// shader example for Processing
// uniform - variables set in Processing sketch using shader.set()
// varying - variables set by Processing itself
#define PROCESSING_COLOR_SHADER
uniform float totalBoids;
uniform vec2[512] boids;

float distanceToNearest() {
    float nearest = 0.0;
    for (int i = 0; i < totalBoids; ++i) {
        vec2 boid = boids[i];
        vec2 pixel = gl_FragCoord.xy;
        float dist = distance(pixel, boid);
        if (isnan(dist) || isinf(dist)) {
        return -1;
        }
        if (dist > nearest) {
            nearest = dist;
        }
    }
    return nearest;
}

void main() {
    //vec4 colour = vec4(d / 2555.0, d / 2555.0, 1.0, 1.0);
    vec4 colour = vec4(1.0, 1.0, 1.0, 1.0);
    float d = distanceToNearest();
    gl_FragColor = colour;
}