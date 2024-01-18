class Wall {
    public float[] a = new float[2];
    public float[] b = new float[2];
    public float[] physicA = new float[2];
    public float[] physicB = new float[2];

    //damit die wände abhänig von ihrer ausrichtung zur kamera eine andere Farbe haben können, für bessere sichtbarkeit
    public float colorValue;

    public Wall(float[] _a, float[] _b) {
        a = _a;
        b = _b;
        if(a[0] == b[0]){
            b[0] += 0.001f;
        }
        if(a[0] <= b[0]){
            physicA[0] = a[0];
            physicB[0] = b[0];
        } else {
            physicA[0] = b[0];
            physicB[0] = a[0];
        }
        if(a[1] <= b[1]){
            physicA[1] = a[1];
            physicB[1] = b[1];
        } else {
            physicA[1] = b[1];
            physicB[1] = a[1];
        }
        colorValue = (float) Math.atan(Math.abs(physicA[0] - physicB[0] / Math.abs(physicA[1] - physicB[1])) / (Math.PI * 0.5f));
        if(colorValue >= 1){
            colorValue = 0.99999f;
        } else  if(colorValue <= 0){
            colorValue = 0;
        }
        System.out.println(colorValue);
    }
}