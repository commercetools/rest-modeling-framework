<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

use Test\Types\ModelClassMap;

class ResourceClassMap
{
    /**
     * @var ClassMap
     */
    private static $classMap;

    private static function getClassMap()
    {
        if (is_null(self::$classMap)) {
            self::$classMap = new ModelClassMap();
        }
        return self::$classMap;
    }

    public static function setClassMap(ClassMap $classMap)
    {
        self::$classMap = $classMap;
    }

    public static function getMappedClass($class)
    {
        return self::getClassMap()->getMappedClass($class);
    }
}
