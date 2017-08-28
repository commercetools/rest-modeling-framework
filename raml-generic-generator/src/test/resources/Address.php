<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Types\JsonObject;

interface Address extends JsonObject {
    const FIELD_STREET = 'street';

    /**
     * @return string
     */
    public function getStreet();
    /**
     * @param string $street
     * @return $this
     */
    public function setStreet($street);

}
