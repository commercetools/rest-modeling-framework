<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

interface Collection extends \IteratorAggregate, \ArrayAccess
{
    public function at($index);

    /**
     * @return MapIterator
     */
    public function getIterator();

    /**
     * @return mixed
     */
    public function current();

    /**
     * @return void
     */
    public function next();

    /**
     * @return mixed
     */
    public function key();

    /**
     * @return void
     */
    public function rewind();

    /**
     * @return bool
     */
    public function valid();

    /**
     * @param mixed $value
     * @return Collection
     */
    public function add($value);
}
