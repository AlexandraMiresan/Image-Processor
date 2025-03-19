import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
    public static void main(String[] args) {
        // The provided images are apple.jpg, flower.jpg, and kitten.jpg
        int[][] imageData = imgToTwoD("https://static.wikia.nocookie.net/himoto-umaruchan/images/a/a2/Umaru%27s_anime_design_%28chibi%29.png/revision/latest?cb=20200411195915");
        // Or load your own image using a URL!
        //int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
        viewImageData(imageData);
        int[][] trimmed = trimBorders(imageData, 60);
        twoDToImage(trimmed, "./trimmed_umaru.jpg");
        int[][] negative = negativeColor(imageData);
        twoDToImage(negative, "./evil_umaru.jpg");
        int[][] stretch = stretchHorizontally(imageData);
        twoDToImage(stretch, "./stretch_umaru.jpg");
        int [][] shrink = shrinkVertically(imageData);
        twoDToImage(shrink, "./shrink_umaru.jpg");
        int[][] invert = invertImage(imageData);
        twoDToImage(invert, "./invert_umaru.jpg");
        int[][] filter = colorFilter(imageData, 50, 200, 19);
        twoDToImage(filter, "./filter_umaru.jpg");
        int[][] blankImg = new int[500][500];

        // int[][] randomImg = paintRandomImage(blankImg);
        // twoDToImage(randomImg, "./rand.jpg");

        // int[] rgba ={255, 192, 203, 255};
        // int[][] rectangleImg = paintRectangle(blankImg, 100, 100, 200, 200, getColorIntValFromRGBA(rgba));
        // twoDToImage(rectangleImg, "./rectangle.jpg");

        int[][] generateImg = generateRectangles(blankImg, 100);
        twoDToImage(generateImg, "./generate.jpg");

        // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
        // Painting with pixels
    }
    // Image Processing Methods
    public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
        // Example Method
        if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
            int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
            for (int i = 0; i < trimmedImg.length; i++) {
                for (int j = 0; j < trimmedImg[i].length; j++) {
                    trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
                }
            }
            return trimmedImg;
        } else {
            System.out.println("Cannot trim that many pixels from the given image.");
            return imageTwoD;
        }
    }
    public static int[][] negativeColor(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] negativeImg = new int[imageTwoD.length][imageTwoD[0].length];

        for(int i = 0; i < negativeImg.length; i++){
            for(int j = 0; j < negativeImg[0].length; j++){
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                rgba[0] = 255 - rgba[0];
                rgba[1] = 255 - rgba[1];
                rgba[2] = 255 - rgba[2];

                negativeImg[i][j] = getColorIntValFromRGBA(rgba);

            }
        }
        return negativeImg;
    }
    public static int[][] stretchHorizontally(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] stretchedImg = new int[imageTwoD.length][imageTwoD.length * 2];

        for(int i = 0; i < imageTwoD.length; i++){
            for(int j = 0; j < imageTwoD[0].length; j++){
                int it = j * 2;
                stretchedImg[i][it] = imageTwoD[i][j];
                stretchedImg[i][it + 1] = imageTwoD[i][j];
            }
        }
        return stretchedImg;
    }
    public static int[][] shrinkVertically(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] shrinkImg = new int[imageTwoD.length / 2][imageTwoD[0].length];

        for(int i = 0; i < imageTwoD[0].length; i++){
            for(int j = 0; j < imageTwoD.length - 1; j += 2){
                shrinkImg[j/2][i] = imageTwoD[j][i];
            }
        }

        return shrinkImg;
    }
    public static int[][] invertImage(int[][] imageTwoD) {
        // TODO: Fill in the code for this method
        int[][] invert = new int[imageTwoD.length][imageTwoD[0].length];

        for(int i = 0; i < imageTwoD.length; i++){
            for(int j = 0; j < imageTwoD[0].length; j++){
                invert[i][j] = imageTwoD[(imageTwoD.length - 1) - i][(imageTwoD[i].length - 1) - j];
            }
        }

        return invert;
    }
    public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
        // TODO: Fill in the code for this method
        int[][] filter = new int[imageTwoD.length][imageTwoD[0].length];

        for(int i = 0; i < imageTwoD.length; i++){
            for(int j = 0; j < imageTwoD[0].length; j++){
                int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
                int newRed = rgba[0] + redChangeValue;
                int newGreen = rgba[1] + greenChangeValue;
                int newBlue = rgba[2] + blueChangeValue;
                if(newRed > 255){
                    newRed = 255;
                }
                if(newBlue > 255){
                    newBlue = 255;
                }
                if(newGreen > 255){
                    newGreen = 255;
                }
                if(newRed < 0){
                    newRed = 0;
                }
                if(newGreen < 0){
                    newGreen = 0;
                }
                if(newBlue < 0){
                    newBlue = 0;
                }

                rgba[0] = newRed;
                rgba[1] = newGreen;
                rgba[2] = newBlue;

                filter[i][j] = getColorIntValFromRGBA(rgba);
            }
        }

        return filter;
    }
    // Painting Methods
    public static int[][] paintRandomImage(int[][] canvas) {
        // TODO: Fill in the code for this method
        Random rand = new Random();

        for(int i = 0; i < canvas.length; i++){
            for(int j = 0; j < canvas[0].length; j++){
                int red = rand.nextInt(256);
                int green = rand.nextInt(256);
                int blue = rand.nextInt(256);

                int[] rgbValues = {red, green, blue, 255};
                canvas[i][j] = getColorIntValFromRGBA(rgbValues);

            }
        }

        return canvas;
    }
    public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
        // TODO: Fill in the code for this method
        for(int i = 0; i < canvas.length; i++){
            for(int j = 0; j < canvas[0].length; j++){
                if(i >= rowPosition && i <= rowPosition + width){
                    if(j >= colPosition && j >= colPosition + height){
                        canvas[i][j] = color;
                    }
                }
            }
        }
        return canvas;
    }
    public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
        // TODO: Fill in the code for this method
        Random rand = new Random();
        for(int i = 0; i < numRectangles; i++){
            int width = rand.nextInt(canvas[0].length);
            int height = rand.nextInt(canvas.length);
            int rowPos = rand.nextInt(canvas.length - height);
            int colPos = rand.nextInt(canvas[0].length - width);
            int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
            int color = getColorIntValFromRGBA(rgba);
            canvas = paintRectangle(canvas, width, height, rowPos, colPos, color);
        }
        return canvas;
    }
    // Utility Methods
    public static int[][] imgToTwoD(String inputFileOrLink) {
        try {
            BufferedImage image = null;
            if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
                URL imageUrl = new URL(inputFileOrLink);
                image = ImageIO.read(imageUrl);
                if (image == null) {
                    System.out.println("Failed to get image from provided URL.");
                }
            } else {
                image = ImageIO.read(new File(inputFileOrLink));
            }
            int imgRows = image.getHeight();
            int imgCols = image.getWidth();
            int[][] pixelData = new int[imgRows][imgCols];
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    pixelData[i][j] = image.getRGB(j, i);
                }
            }
            return pixelData;
        } catch (Exception e) {
            System.out.println("Failed to load image: " + e.getLocalizedMessage());
            return null;
        }
    }
    public static void twoDToImage(int[][] imgData, String fileName) {
        try {
            int imgRows = imgData.length;
            int imgCols = imgData[0].length;
            BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < imgRows; i++) {
                for (int j = 0; j < imgCols; j++) {
                    result.setRGB(j, i, imgData[i][j]);
                }
            }
            File output = new File(fileName);
            ImageIO.write(result, "jpg", output);
        } catch (Exception e) {
            System.out.println("Failed to save image: " + e.getLocalizedMessage());
        }
    }
    public static int[] getRGBAFromPixel(int pixelColorValue) {
        Color pixelColor = new Color(pixelColorValue);
        return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
    }
    public static int getColorIntValFromRGBA(int[] colorData) {
        if (colorData.length == 4) {
            Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
            return color.getRGB();
        } else {
            System.out.println("Incorrect number of elements in RGBA array.");
            return -1;
        }
    }
    public static void viewImageData(int[][] imageTwoD) {
        if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
            int[][] rawPixels = new int[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rawPixels[i][j] = imageTwoD[i][j];
                }
            }
            System.out.println("Raw pixel data from the top left corner.");
            System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
            int[][][] rgbPixels = new int[3][3][4];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
                }
            }
            System.out.println();
            System.out.println("Extracted RGBA pixel data from top the left corner.");
            for (int[][] row : rgbPixels) {
                System.out.print(Arrays.deepToString(row) + System.lineSeparator());
            }
        } else {
            System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
        }
    }
}