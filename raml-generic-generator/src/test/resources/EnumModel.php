<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObjectModel;

class EnumModel extends JsonObjectModel implements Enum {
    /**
     * @var string
     */
    private $key;
    /**
     * @var string
     */
    private $label;

    /**
     * @return string
     */
    public function getKey()
    {
        if (is_null($this->key)) {
            $value = $this->raw(Enum::FIELD_KEY);
            $this->key = (string)$value;
        }
        return $this->key;
    }
    /**
     * @return string
     */
    public function getLabel()
    {
        if (is_null($this->label)) {
            $value = $this->raw(Enum::FIELD_LABEL);
            $this->label = (string)$value;
        }
        return $this->label;
    }

    /**
     * @param string $key
     * @return $this
     */
    public function setKey($key)
    {
        $this->key = (string)$key;

        return $this;
    }
    /**
     * @param string $label
     * @return $this
     */
    public function setLabel($label)
    {
        $this->label = (string)$label;

        return $this;
    }

}
