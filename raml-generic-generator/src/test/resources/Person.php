<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Person extends JsonObject {
    const FIELD_NAME = 'name';

    /**
     * @return string
     */
    public function getName();
    /**
     * @param string $name
     * @return $this
     */
    public function setName($name);

}
