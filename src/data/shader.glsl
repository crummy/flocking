// shader example for Processing
// uniform - variables set in Processing sketch using shader.set()
// varying - variables set by Processing itself
uniform int totalBoids;
uniform float[512] boids;

float distanceToNearest() {
    float nearest = 9999.9;
    for (int i = 0; i < totalBoids; i = i + 2) {
        vec2 boid = vec2(boids[i], boids[i+1]);
        vec2 pixel = gl_FragCoord.xy;
        float dist = length(pixel - boid);
        if (isnan(dist) || isinf(dist)) {
            return -1;
        }
        nearest = min(nearest, dist);
    }
    return nearest;
}

void main() {
    float d = distanceToNearest();
    vec4 colour = vec4(d / 255.0, d / 255.0, d / 255.0, 1.0);
    gl_FragColor = colour;
}