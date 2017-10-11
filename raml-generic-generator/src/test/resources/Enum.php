<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

use Test\Base\JsonObject;

interface Enum extends JsonObject {
    const FIELD_KEY = 'key';
    const FIELD_LABEL = 'label';

    /**
     * @return string
     */
    public function getKey();

    /**
     * @return string
     */
    public function getLabel();

    /**
     * @param string $key
     * @return $this
     */
    public function setKey($key);

    /**
     * @param string $label
     * @return $this
     */
    public function setLabel($label);

}
