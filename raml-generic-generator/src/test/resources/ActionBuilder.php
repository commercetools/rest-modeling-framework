<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Base;

use Test\Types\ModelClassMap;

class ActionBuilder implements MapperAware
{
    private $resultMapper;

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

    protected function mapData($class, $data)
    {
        return $this->getMapper()->mapData($class, $data);
    }
}
