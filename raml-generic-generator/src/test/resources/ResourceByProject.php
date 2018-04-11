<?php
declare(strict_types = 1);
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Request;

use Test\Client\Resource;

class ResourceByProject extends Resource
{
    /**
     * @return ResourceByProjectCategories
     */
    public function categories(): ResourceByProjectCategories {
        return new ResourceByProjectCategories($this->getUri() . '/categories', $this->getArgs(), $this->getMapper());
    }


    /**
     * @return ByProjectGet
     */
    public function get(): ByProjectGet {
        $args = $this->getArgs();
        return new ByProjectGet($args['project']);
    }

}
