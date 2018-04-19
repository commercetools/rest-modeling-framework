<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Attribute extends JsonObject
{
    const FIELD_NAME = 'name';
    const FIELD_VALUE = 'value';

    /**
     * @return string
     */
    public function getName();

    /**
     * @return mixed
     */
    public function getValue();

    /**
     * @param string $name
     * @return $this
     */
    public function setName(string $name);

    /**
     * @param mixed $value
     * @return $this
     */
    public function setValue($value);

    /**
     * @return string
     */
    public function getValueAsString();

    /**
     * @return Money
     */
    public function getValueAsMoney();

    /**
     * @return Enum
     */
    public function getValueAsEnum();

}
