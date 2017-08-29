<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

interface JsonObject extends \JsonSerializable
{
    /**
     * @return bool
     */
    public function isPresent($field);

    /**
     * @param array $data
     * @return static
     */
    public static function fromArray(array $data);
}
