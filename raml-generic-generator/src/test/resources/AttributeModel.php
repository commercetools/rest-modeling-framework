<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Exception\InvalidArgumentException;
use Test\Base\JsonObjectModel;

class AttributeModel extends JsonObjectModel implements Attribute
{
    /**
     * @var string
     */
    protected $name;
    /**
     * @var mixed
     */
    protected $value;

    /**
     * @return string
     */
    public function getName()
    {
        if (is_null($this->name)) {
            $value = $this->raw(Attribute::FIELD_NAME);
            $value = (string)$value;
            $this->name = $value;
        }
        return $this->name;
    }
    /**
     * @return mixed
     */
    public function getValue()
    {
        if (is_null($this->value)) {
            $value = $this->raw(Attribute::FIELD_VALUE);
            $this->value = $value;
        }
        return $this->value;
    }

    /**
     * @param string $name
     * @return $this
     */
    public function setName(string $name)
    {
        $this->name = (string)$name;

        return $this;
    }
    /**
     * @param $value
     * @return $this
     */
    public function setValue($value)
    {
        $this->value = $value;

        return $this;
    }

    /**
     * @return string
     */
    public function getValueAsString()
    {
        if (is_null($this->value)) {
            $value = $this->raw(Attribute::FIELD_VALUE);
            $value = (string)$value;
            $this->value = $value;
        }
        return $this->value;
    }

    /**
     * @return Money
     */
    public function getValueAsMoney()
    {
        if (is_null($this->value)) {
            $value = $this->raw(Attribute::FIELD_VALUE);
            if (is_null($value)) {
                return $this->mapData(Money::class, null);
            }
            $value = $this->mapData(Money::class, $value);

            $this->value = $value;
        }
        return $this->value;
    }

    /**
     * @return Enum
     */
    public function getValueAsEnum()
    {
        if (is_null($this->value)) {
            $value = $this->raw(Attribute::FIELD_VALUE);
            if (is_null($value)) {
                return $this->mapData(Enum::class, null);
            }
            $value = $this->mapData(Enum::class, $value);

            $this->value = $value;
        }
        return $this->value;
    }

}
