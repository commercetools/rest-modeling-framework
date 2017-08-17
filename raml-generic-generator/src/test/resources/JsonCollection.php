<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

class JsonCollection implements Collection, \JsonSerializable
{
    private $rawData;
    private $keys;
    private $indexes = [];
    private $iterator;

    public function __construct(array $data = [])
    {
        $this->keys = array_keys($data);
        $this->index($data);
        $this->rawData = $data;
        $this->iterator = $this->getIterator();
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
    public function toArray()
    {
        return $this->rawData;
    }

    public function __set($name, $value)
    {
        throw new \BadMethodCallException('Setting values is not allowed');
    }

    protected function index($data)
    {
    }

    protected function raw($field)
    {
        if (isset($this->rawData[$field])) {
            return $this->rawData[$field];
        }
        return null;
    }

    public function at($index)
    {
        return $this->map($this->raw($index), $index);
    }

    public function map($data, $index)
    {
        return $data;
    }

    protected function addToIndex($index, $key, $value)
    {
        $this->indexes[$index][$key] = $value;
    }

    protected function valueByKey($index, $key)
    {
        return isset($this->indexes[$index][$key]) ? $this->at($this->indexes[$index][$key]) : null;
    }

    /**
     * @return MapIterator
     */
    public function getIterator()
    {
        return new MapIterator($this->rawData, [$this, 'map']);
    }

    /**
     * @inheritDoc
     */
    public function current()
    {
        return $this->iterator->current();
    }

    /**
     * @inheritDoc
     */
    public function next()
    {
        $this->iterator->next();
    }

    /**
     * @inheritDoc
     */
    public function key()
    {
        $this->iterator->key();
    }

    /**
     * @inheritDoc
     */
    public function valid()
    {
        $this->iterator->valid();
    }

    /**
     * @inheritDoc
     */
    public function rewind()
    {
        $this->iterator->rewind();
    }
}
