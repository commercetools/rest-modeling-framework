<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

use Test\Types\ModelClassMap;

class JsonObjectModel implements JsonObject, MapperAware
{
    private $rawData;
    private $resultMapper;

    public function __construct(array $data = null)
    {
        $this->rawData = $data;
    }

    /**
     * @param Mapper $mapper
     */
    public function setMapper(Mapper $mapper)
    {
        $this->resultMapper = $mapper;
    }

    /**
     * @returns Mapper
     */
    public function getMapper()
    {
        if (is_null($this->resultMapper)) {
            $this->resultMapper = new ResultMapper(new ModelClassMap());
        }
        return $this->resultMapper;
    }

    protected function resolveDiscriminator($class, $data)
    {
        return $this->getMapper()->resolveDiscriminator($class, $class::DISCRIMINATOR, $class::SUB_TYPES, $data);
    }

    protected function mapData($class, $data)
    {
        return $this->getMapper()->mapData($class, $data);
    }

    final protected function raw($field)
    {
        if (isset($this->rawData[$field])) {
            return $this->rawData[$field];
        }
        return null;
    }


    public function isPresent()
    {
        return !is_null($this->rawData);
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
    protected function toArray()
    {
        $rawData = is_array($this->rawData) ? $this->rawData : [];
        $data = array_filter(
            get_object_vars($this),
            function ($value, $key) {
                if ($key == 'rawData' || $key == 'resultMapper') {
                    return false;
                }
                return !is_null($value);
            },
            ARRAY_FILTER_USE_BOTH
        );
        $data = array_merge($rawData, $data);
        return $data;
    }
}
