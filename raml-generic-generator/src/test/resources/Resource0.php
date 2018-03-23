<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Request;

use Test\Client\Resource;

class Resource0 extends Resource
{
    /**
     * @return Resource1
     */
    public function categories(): Resource1 {
        return new Resource1($this->getUri() . '/categories', $this->getArgs(), $this->getMapper());
    }


    /**
     * @return ByProjectGet
     */
    public function get(): ByProjectGet {
        $args = $this->getArgs();
        return new ByProjectGet($args['project']);
    }

}
