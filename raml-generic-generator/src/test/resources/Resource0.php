<?php
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
    public function categories() {
        return new Resource1($this->getUri() . '/categories', $this->getArgs());
    }


    /**
     * @return ByProjectGet
     */
    public function get() {
        $args = $this->getArgs();
        return new ByProjectGet($args['project']);
    }

}
