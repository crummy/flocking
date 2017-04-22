// shader example for Processing
// uniform - variables set in Processing sketch using shader.set()
// varying - variables set by Processing itself

uniform float totalBoids;
uniform vec2[1024] boids;

float distanceToNearest() {
    float nearest = 9999.0;
    for (int i = 0; i < 1024; ++i) {
        vec2 boid = boids[i];
        vec2 pixel = gl_FragCoord.xy;
        float dist = distance(pixel, boid);
        if (dist < nearest) {
            nearest = dist;
        }
    }
    return nearest;
}

void main() {
    float d = distanceToNearest();
    vec4 colour = vec4(d / 255, d / 255, d / 255, 1.0);
    gl_FragColor = colour;
}