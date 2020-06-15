package com.kqds.core.util.db.generics;

public class DataTableEntity
{
  private int columnCount = 0;
  private String[] columnNames;
  private int[] columnTypes;
  
  public DataTableEntity()
  {
    this(0);
  }
  
  public DataTableEntity(int columnCount)
  {
    this.columnCount = columnCount;
    this.columnNames = new String[columnCount];
    this.columnTypes = new int[columnCount];
  }
  
  public int getColumnCount()
  {
    return this.columnCount;
  }
  
  public String[] getColumnNames()
  {
    return this.columnNames;
  }
  
  public String getColumnName(int index)
  {
    if (index <= this.columnCount) {
      return this.columnNames[index];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void setColumnNames(String[] columnNames)
  {
    this.columnNames = columnNames;
  }
  
  public void setColumnName(String columnName, int index)
  {
    if (index <= this.columnCount) {
      this.columnNames[index] = columnName;
    } else {
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  
  public int[] getColumnTypes()
  {
    return this.columnTypes;
  }
  
  public int getColumnType(int index)
  {
    if (index <= this.columnCount) {
      return this.columnTypes[index];
    }
    throw new ArrayIndexOutOfBoundsException();
  }
  
  public void setColumnTypes(int[] columnTypes)
  {
    this.columnTypes = columnTypes;
  }
  
  public void setColumnType(int columnType, int index)
  {
    if (index <= this.columnCount) {
      this.columnTypes[index] = columnType;
    } else {
      throw new ArrayIndexOutOfBoundsException();
    }
  }
}
