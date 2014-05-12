/**
 * Copyright 2012 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package parquet.column.statistics;

import parquet.bytes.BytesUtils;

public class FloatStatistics extends Statistics{

  private float max;
  private float min;

  @Override
  public void updateStats(float value) {
    if (this.isEmpty()) {
      initializeStats(value, value);
    } else {
      updateStats(value, value);
    }
  }

  @Override
  public void mergeStatisticsMinMax(Statistics stats) {
    FloatStatistics floatStats = (FloatStatistics)stats;
    if (this.isEmpty()) {
      initializeStats(floatStats.getMin(), floatStats.getMax());
    } else {
      updateStats(floatStats.getMin(), floatStats.getMax());
    }
  }

  @Override
  public void setMinMaxFromBytes(byte[] minBytes, byte[] maxBytes) {
    max = Float.intBitsToFloat(BytesUtils.bytesToInt(maxBytes));
    min = Float.intBitsToFloat(BytesUtils.bytesToInt(minBytes));
    this.markAsNotEmpty();
  }

  @Override
  public byte[] getMaxBytes() {
    return BytesUtils.intToBytes(Float.floatToIntBits(max));
  }

  @Override
  public byte[] getMinBytes() {
    return BytesUtils.intToBytes(Float.floatToIntBits(min));
  }

  public void updateStats(float min_value, float max_value) {
    if (min_value < min) { min = min_value; }
    if (max_value > max) { max = max_value; }
  }

  public void initializeStats(float min_value, float max_value) {
      min = min_value;
      max = max_value;
      this.markAsNotEmpty();
  }

  public float getMax() {
    return max;
  }

  public float getMin() {
    return min;
  }

  public void setMinMax(float min, float max) {
    this.max = max;
    this.min = min;
    this.markAsNotEmpty();
  }
}