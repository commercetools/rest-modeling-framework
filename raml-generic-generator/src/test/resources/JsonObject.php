<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class JsonObject implements \JsonSerializable
{
    private $rawData;

    public function __construct(array $data = [])
    {
        $this->rawData = $data;
    }

    protected function raw($field)
    {
        if (isset($this->rawData[$field])) {
            return $this->rawData[$field];
        }
        return null;
    }

    public function jsonSerialize()
    {
        return $this->toArray();
    }

    /**
     * @inheritdoc
     */
    public static function fromArray(array $data)
    {
        return new static($data);
    }

    /**
     * @inheritdoc
     */
    private function toArray()
    {
        $data = array_filter(
            get_object_vars($this),
            function ($value, $key) {
                if ($key == 'rawData') {
                    return false;
                }
                return !is_null($value);
            },
            ARRAY_FILTER_USE_BOTH
        );
        $data = array_merge($this->rawData, $data);
        return $data;
    }

    public function __set($name, $value)
    {
        throw new \BadMethodCallException('Setting values is not allowed');
    }
}