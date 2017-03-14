package EditDistance;

import java.util.Arrays;
import org.apache.log4j.Logger;
import org.apache.lucene.search.spell.StringDistance;

public class QwertyEditDistance
  implements StringDistance
{
  private static Logger LOGGER = Logger.getLogger(QwertyEditDistance.class);
  int[][] operationCost = new int[26][26];

  public float getDistance(String target, String other) { initializeCostMatrix();

    LOGGER.error("Qwerty edit distance working..");
    LOGGER.error("Target: " + target);

    char[] targetCharArray = target.toCharArray();
    int targetLength = targetCharArray.length;
    int[] previous = new int[targetLength + 1];
    int[] current = new int[targetLength + 1];

    int otherLength = other.length();
    if ((targetLength == 0) || (otherLength == 0)) {
      if (targetLength == otherLength) {
        return 1.0F;
      }

      return 0.0F;
    }

    for (int targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
      previous[targetIndex] = targetIndex;
    }

    for (int otherIndex = 1; otherIndex <= otherLength; otherIndex++) {
      char other_j = other.charAt(otherIndex - 1);
      current[0] = otherIndex;

      for (targetIndex = 1; targetIndex <= targetLength; targetIndex++)
      {
        int cost;
        int cost;
        if (targetCharArray[(targetIndex - 1)] == other_j) {
          cost = 0;
        }
        else {
          int targetAscii = Character.toUpperCase(targetCharArray[(targetIndex - 1)]);
          int otherAscii = Character.toUpperCase(other_j);

          cost = this.operationCost[(targetAscii - 65)][(otherAscii - 65)];
        }

        current[targetIndex] = Math.min(Math.min(current[(targetIndex - 1)] + 1, previous[targetIndex] + 1), previous[(targetIndex - 1)] + cost);
      }

      int[] temp = previous;
      previous = current;
      current = temp;
    }

    return 1.0F - previous[targetLength] / Math.max(other.length(), targetCharArray.length);
  }

  public void initializeCostMatrix()
  {
    for (int[] row : this.operationCost) {
      Arrays.fill(row, 50);
    }

    for (int i = 0; i < 26; i++) {
      for (int j = 0; j < 26; j++) {
        if (i == j) {
          this.operationCost[i][j] = 0;
        }
      }
    }

    this.operationCost[0][16] = 1;
    this.operationCost[0][18] = 1;
    this.operationCost[0][22] = 5;
    this.operationCost[0][25] = 5;

    this.operationCost[1][6] = 5;
    this.operationCost[1][7] = 5;
    this.operationCost[1][13] = 1;
    this.operationCost[1][21] = 1;

    this.operationCost[2][3] = 5;
    this.operationCost[2][5] = 5;
    this.operationCost[2][21] = 1;
    this.operationCost[2][23] = 1;

    this.operationCost[3][2] = 5;
    this.operationCost[3][4] = 1;
    this.operationCost[3][5] = 1;
    this.operationCost[3][17] = 5;
    this.operationCost[3][18] = 1;
    this.operationCost[3][23] = 5;

    this.operationCost[4][3] = 1;
    this.operationCost[4][17] = 1;
    this.operationCost[4][18] = 5;
    this.operationCost[4][22] = 1;

    this.operationCost[5][2] = 5;
    this.operationCost[5][3] = 1;
    this.operationCost[5][6] = 1;
    this.operationCost[5][17] = 1;
    this.operationCost[5][19] = 5;
    this.operationCost[5][21] = 5;

    this.operationCost[6][1] = 5;
    this.operationCost[6][3] = 10;
    this.operationCost[6][5] = 1;
    this.operationCost[6][7] = 1;
    this.operationCost[6][17] = 10;
    this.operationCost[6][19] = 1;
    this.operationCost[6][21] = 5;
    this.operationCost[6][24] = 0;

    this.operationCost[7][1] = 5;
    this.operationCost[7][6] = 1;
    this.operationCost[7][9] = 1;
    this.operationCost[7][13] = 5;
    this.operationCost[7][19] = 10;
    this.operationCost[7][20] = 10;
    this.operationCost[7][24] = 5;

    this.operationCost[8][9] = 5;
    this.operationCost[8][10] = 1;
    this.operationCost[8][11] = 5;
    this.operationCost[8][14] = 1;
    this.operationCost[8][20] = 1;

    this.operationCost[9][7] = 1;
    this.operationCost[9][8] = 10;
    this.operationCost[9][10] = 1;
    this.operationCost[9][12] = 5;
    this.operationCost[9][13] = 5;
    this.operationCost[9][20] = 5;
    this.operationCost[9][24] = 10;

    this.operationCost[10][8] = 5;
    this.operationCost[10][9] = 1;
    this.operationCost[10][11] = 1;
    this.operationCost[10][12] = 5;
    this.operationCost[10][14] = 5;

    this.operationCost[11][8] = 10;
    this.operationCost[11][10] = 1;
    this.operationCost[11][14] = 1;
    this.operationCost[11][15] = 5;

    this.operationCost[12][9] = 5;
    this.operationCost[12][10] = 5;
    this.operationCost[12][13] = 1;

    this.operationCost[13][1] = 1;
    this.operationCost[13][7] = 5;
    this.operationCost[13][9] = 5;
    this.operationCost[13][12] = 1;

    this.operationCost[14][8] = 1;
    this.operationCost[14][10] = 5;
    this.operationCost[14][11] = 1;
    this.operationCost[14][15] = 1;

    this.operationCost[15][11] = 5;
    this.operationCost[15][14] = 1;

    this.operationCost[16][0] = 1;
    this.operationCost[16][22] = 1;

    this.operationCost[17][3] = 5;
    this.operationCost[17][4] = 1;
    this.operationCost[17][5] = 1;
    this.operationCost[17][6] = 10;
    this.operationCost[17][19] = 5;

    this.operationCost[18][0] = 1;
    this.operationCost[18][3] = 1;
    this.operationCost[18][4] = 5;
    this.operationCost[18][22] = 1;
    this.operationCost[18][23] = 5;
    this.operationCost[18][25] = 5;

    this.operationCost[19][5] = 5;
    this.operationCost[19][6] = 1;
    this.operationCost[19][7] = 10;
    this.operationCost[19][17] = 1;
    this.operationCost[19][24] = 1;

    this.operationCost[20][7] = 5;
    this.operationCost[20][8] = 1;
    this.operationCost[20][9] = 1;
    this.operationCost[20][10] = 10;
    this.operationCost[20][24] = 1;

    this.operationCost[21][1] = 1;
    this.operationCost[21][2] = 1;
    this.operationCost[21][5] = 5;
    this.operationCost[21][6] = 5;

    this.operationCost[22][0] = 5;
    this.operationCost[22][3] = 10;
    this.operationCost[22][4] = 1;
    this.operationCost[22][16] = 1;
    this.operationCost[22][18] = 1;

    this.operationCost[23][2] = 1;
    this.operationCost[23][3] = 5;
    this.operationCost[23][18] = 5;
    this.operationCost[23][25] = 1;

    this.operationCost[24][6] = 5;
    this.operationCost[24][7] = 1;
    this.operationCost[24][9] = 10;
    this.operationCost[24][19] = 1;
    this.operationCost[24][20] = 1;

    this.operationCost[25][0] = 1;
    this.operationCost[25][18] = 1;
    this.operationCost[25][23] = 1;
  }
}
