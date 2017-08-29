<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

abstract class ClassMap
{
    protected static $types = [];

    public function getMappedClass($class)
    {
        if (isset(static::$types[$class])) {
            return static::$types[$class];
        }
        return $class;
    }
}
