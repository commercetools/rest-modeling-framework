<?php
/**
 * This file has been auto generated
 * Do not change it
 */

namespace Test\Types;

interface Animal extends JsonObject {
    const FIELD_KIND = 'kind';

    /**
     * @return string
     */
    public function getKind();
    /**
     * @param string $kind
     * @return $this
     */
    public function setKind($kind);

}
