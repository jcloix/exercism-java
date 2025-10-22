class PascalsTriangleGenerator {

    public static int[][] generateTriangle(int rows) {
        int[][] triangle = new int[rows][];

        for (int i = 0; i < rows; i++) {
            triangle[i] = new int[i + 1]; // each row has i+1 elements
            triangle[i][0] = 1;           // first element is always 1
            triangle[i][i] = 1;           // last element is always 1

            // Fill in the inner elements using values from the previous row
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }

        return triangle;
    }

}