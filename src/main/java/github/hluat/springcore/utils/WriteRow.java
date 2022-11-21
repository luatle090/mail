package github.hluat.springcore.utils;

import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface WriteRow<T> {
   void writeRow(T t, Row row);
}