public class Matrix {

    public double[][] a;
    public int m;
    public int n;

    public Matrix(int m) {
        this(m, m);
    }

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        a = new double[m][n];
    }

    public Matrix(int m, int n, double left, double right) {
        this(m, n);
        Matrix.random(this, left, right);
    }

    public Matrix(double[] matrix) {
        double[][] result = new double[1][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[0][i] = matrix[i];
        }
        this.m = result.length;
        this.n = result[0].length;
        this.a = result;
    }

    public Matrix(Double[] matrix) {
        double[][] result = new double[1][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[0][i] = matrix[i];
        }
        this.m = result.length;
        this.n = result[0].length;
        this.a = result;
    }

    public void set(int i, int j, double s) {
        a[i][j] = s;
    }

    public double get(int i, int j) {
        return a[i][j];
    }

    public Matrix transpose() {
        Matrix X = new Matrix(n, m);
        double[][] C = X.a;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (Double.isNaN(a[i][j])) {
                    System.out.println("Nan");
                }
                C[j][i] = a[i][j];
            }
        }
        return X;
    }

    public Matrix abs() {
        Matrix X = new Matrix(n, m);
        double[][] C = X.a;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (Double.isNaN(a[i][j])) {
                    System.out.println("Nan");
                }
                C[i][j] = Math.abs(a[i][j]);
            }
        }
        return X;
    }

    public double sum() {
        double sum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += a[i][j];
            }
        }
        return sum;
    }

    public Matrix times(Matrix B) {
        Matrix X = new Matrix(m, B.n);
        double[][] C = X.a;
        double[] Bcolj = new double[n];
        for (int j = 0; j < B.n; j++) {
            for (int k = 0; k < n; k++) {
                Bcolj[k] = B.a[k][j];
            }
            for (int i = 0; i < m; i++) {
                double[] Arowi = a[i];
                double s = 0;
                for (int k = 0; k < n; k++) {
                    s += Arowi[k] * Bcolj[k];
                    if (Double.isInfinite(s)) {
                        s = s > 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
                    }
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    public Matrix times(double s) {
        Matrix X = new Matrix(m, n);
        double[][] C = X.a;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = s * a[i][j];
                if (Double.isInfinite(C[i][j])) {
                    C[i][j] = C[i][j] > 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
                }
            }
        }
        return X;
    }

    public Matrix minus(Matrix B) {
        Matrix X = new Matrix(m, n);
        double[][] C = X.a;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = a[i][j] - B.a[i][j];
                if (Double.isInfinite(C[i][j])) {
                    C[i][j] = C[i][j] > 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
                }
            }
        }
        return X;
    }

    public Matrix plus(Matrix B) {
        Matrix X = new Matrix(m, n);
        double[][] C = X.a;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = a[i][j] + B.a[i][j];
                if (Double.isInfinite(C[i][j])) {
                    C[i][j] = C[i][j] > 0 ? Double.MAX_VALUE : Double.MIN_VALUE;
                }
            }
        }
        return X;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s = String.valueOf((a[i][j]));
                int padding = Math.max(1, n - s.length());
                for (int k = 0; k < padding; k++)
                    System.out.print(' ');
                System.out.print(s);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void random(Matrix matrix, double min, double max) {
        double[][] mass = matrix.a;
        for (int i = 0; i < matrix.m; i++) {
            for (int j = 0; j < matrix.n; j++) {
                mass[i][j] = (Math.random() * (max - min) + min)/10;
            }
        }
    }

    public Matrix getColumn(int c){
        Matrix column = new Matrix(this.m, 1);
        for (int j = 0; j < this.m; j++) {
          column.a[j][0] = this.a[j][c];
        }
        return  column;
    }

}