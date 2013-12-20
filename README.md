AndroidModelView
================

A semi-ORM framework which basically re-engineer yii data access frameork into java android

Usage
=====

1. Create class that extends `Model` or `FlexibleModel`
2. Override the TABLE_STRING on that model
3. Add the string table to the query variable on `MySqliteHelper` class on `onCreate` method
4. Implement the class abstract methods. I sugest to extends `FlexibleModel` because it has implemented many abstract method. The abstract method needed to be implemented is `fillFieldFromCursor`, `fromCursor`, `toContentValue` and others :P

